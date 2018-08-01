package com.sync.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {
    public static String doGet(String httpurl) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// ���ؽ���ַ���
        try {
            // ����Զ��url���Ӷ���
            URL url = new URL(httpurl);
            // ͨ��Զ��url���Ӷ����һ�����ӣ�ǿת��httpURLConnection��
            connection = (HttpURLConnection) url.openConnection();
            // �������ӷ�ʽ��get
            connection.setRequestMethod("GET");
            // �������������������ĳ�ʱʱ�䣺2000����
            connection.setConnectTimeout(2000);
            // ���ö�ȡԶ�̷��ص�����ʱ�䣺3000����
            connection.setReadTimeout(3000);
            // ��������
            connection.connect();
            // ͨ��connection���ӣ���ȡ������
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // ��װ������is����ָ���ַ���
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // �������
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
        	throw new Exception(" http MalformedURLException fail", e);
        } catch (IOException e) {
        	throw new Exception(" http IOException fail", e);
        } finally {
            // �ر���Դ
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                	throw new Exception(" http IOException fail", e);
                }
            }

            connection.disconnect();// �ر�Զ������
        }

        return result;
    }

    public static String doPost(String httpUrl, String param) throws Exception {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // ͨ��Զ��url���Ӷ��������
            connection = (HttpURLConnection) url.openConnection();
            // ������������ʽ
            connection.setRequestMethod("POST");
            // ��������������������ʱʱ�䣺15000����
            connection.setConnectTimeout(15000);
            // ���ö�ȡ�����������������ݳ�ʱʱ�䣺60000����
            connection.setReadTimeout(60000);

            // Ĭ��ֵΪ��false������Զ�̷�������������/д����ʱ����Ҫ����Ϊtrue
            connection.setDoOutput(true);
            // Ĭ��ֵΪ��true����ǰ��Զ�̷����ȡ����ʱ������Ϊtrue���ò������п���
            connection.setDoInput(true);
            // ���ô�������ĸ�ʽ:�������Ӧ���� name1=value1&name2=value2 ����ʽ��
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // ���ü�Ȩ��Ϣ��Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // ͨ�����Ӷ����ȡһ�������
            os = connection.getOutputStream();
            // ͨ����������󽫲���д��ȥ/�����ȥ,����ͨ���ֽ�����д����
            os.write(param.getBytes());
            // ͨ�����Ӷ����ȡһ������������Զ�̶�ȡ
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // ��������������а�װ:charset���ݹ�����Ŀ���Ҫ��������
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // ѭ������һ��һ�ж�ȡ����
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
        	throw new Exception(" http MalformedURLException fail", e);
        } catch (IOException e) {
        	throw new Exception(" http IOException fail", e);
        } finally {
            // �ر���Դ
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                	throw new Exception(" http IOException fail", e);
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                	throw new Exception(" http IOException fail", e);
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                	throw new Exception(" http IOException fail", e);
                }
            }
            // �Ͽ���Զ�̵�ַurl������
            connection.disconnect();
        }
        return result;
    }
}