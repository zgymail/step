package hr.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class FileOperate {
	 public FileOperate() {
	    }

	    /**
	       * �½�Ŀ¼
	       * @param folderPath String �� c:/fqf
	       * @return boolean
	       */
	    public static void newFolder(String folderPath) {
	        try {
	            String filePath = folderPath;
	            filePath = filePath.toString();

	            java.io.File myFilePath = new java.io.File(filePath);

	            if (!myFilePath.exists()) {
	                myFilePath.mkdir();
	            }
	        } catch (Exception e) {
	            System.out.println("�½�Ŀ¼��������");
	            e.printStackTrace();
	        }
	    }

	    /**
	       * �½��ļ�
	       * @param filePathAndName String �ļ�·������� ��c:/fqf.txt
	       * @param fileContent String �ļ�����
	       * @return boolean
	       */
	    public static void newFile(String filePathAndName, String fileContent) {
	        try {
	            String filePath = filePathAndName;
	            filePath = filePath.toString();

	            File myFilePath = new File(filePath);

	            if (!myFilePath.exists()) {
	                myFilePath.createNewFile();
	            }

	            FileWriter resultFile = new FileWriter(myFilePath);
	            PrintWriter myFile = new PrintWriter(resultFile);
	            String strContent = fileContent;
	            myFile.println(strContent);
	            resultFile.close();
	        } catch (Exception e) {
	            System.out.println("�½�Ŀ¼��������");
	            e.printStackTrace();
	        }
	    }

	    /**
	       * ɾ���ļ�
	       * @param filePathAndName String �ļ�·������� ��c:/fqf.txt
	       * @param fileContent String
	       * @return boolean
	       */
	    public static void delFile(String filePathAndName) {
	        try {
	            String filePath = filePathAndName;
	            filePath = filePath.toString();

	            java.io.File myDelFile = new java.io.File(filePath);
	            myDelFile.delete();
	        } catch (Exception e) {
	            System.out.println("ɾ���ļ���������");
	            e.printStackTrace();
	        }
	    }

	    /**
	       * ɾ���ļ���
	       * @param filePathAndName String �ļ���·������� ��c:/fqf
	       * @param fileContent String
	       * @return boolean
	       */
	    public static void delFolder(String folderPath) {
	        try {
	            delAllFile(folderPath); //ɾ����������������

	            String filePath = folderPath;
	            filePath = filePath.toString();

	            java.io.File myFilePath = new java.io.File(filePath);
	            myFilePath.delete(); //ɾ����ļ���
	        } catch (Exception e) {
	            System.out.println("ɾ���ļ��в�������");
	            e.printStackTrace();
	        }
	    }

	    /**
	       * ɾ���ļ�������������ļ�
	       * @param path String �ļ���·�� �� c:/fqf
	       */
	    public static void delAllFile(String path) {
	        File file = new File(path);

	        if (!file.exists()) {
	            return;
	        }

	        if (!file.isDirectory()) {
	            return;
	        }

	        String[] tempList = file.list();
	        File temp = null;

	        for (int i = 0; i < tempList.length; i++) {
	            if (path.endsWith(File.separator)) {
	                temp = new File(path + tempList[i]);
	            } else {
	                temp = new File(path + File.separator + tempList[i]);
	            }

	            if (temp.isFile()) {
	                temp.delete();
	            }

	            if (temp.isDirectory()) {
	                delAllFile(path + "/" + tempList[i]); //��ɾ���ļ���������ļ�
	                delFolder(path + "/" + tempList[i]); //��ɾ����ļ���
	            }
	        }
	    }

	    /**
	       * ���Ƶ����ļ�
	       * @param oldPath String ԭ�ļ�·�� �磺c:/fqf.txt
	       * @param newPath String ���ƺ�·�� �磺f:/fqf.txt
	       * @return boolean
	       */
	    public static void copyFile(String oldPath, String newPath) {
	        try {
	            int bytesum = 0;
	            int byteread = 0;
	            File oldfile = new File(oldPath);

	            if (oldfile.exists()) { //�ļ�����ʱ

	                InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ�
	                FileOutputStream fs = new FileOutputStream(newPath);
	                byte[] buffer = new byte[1444];
	                int length;

	                while ((byteread = inStream.read(buffer)) != -1) {
	                    bytesum += byteread; //�ֽ����ļ���С
	                    fs.write(buffer, 0, byteread);
	                }

	                inStream.close();
	            }
	        } catch (Exception e) {
	            System.out.println("���Ƶ����ļ���������");
	            e.printStackTrace();
	        }
	    }

	    /**
	       * ��������ļ�������
	       * @param oldPath String ԭ�ļ�·�� �磺c:/fqf
	       * @param newPath String ���ƺ�·�� �磺f:/fqf/ff
	       * @return boolean
	       */
	    public static void copyFolder(String oldPath, String newPath) {
	        try {
	            (new File(newPath)).mkdirs(); //����ļ��в����� �������ļ���

	            File a = new File(oldPath);
	            String[] file = a.list();
	            File temp = null;

	            for (int i = 0; i < file.length; i++) {
	                if (oldPath.endsWith(File.separator)) {
	                    temp = new File(oldPath + file[i]);
	                } else {
	                    temp = new File(oldPath + File.separator + file[i]);
	                }

	                if (temp.isFile()) {
	                    FileInputStream input = new FileInputStream(temp);
	                    FileOutputStream output = new FileOutputStream(newPath +
	                            "/" + (temp.getName()).toString());
	                    byte[] b = new byte[1024 * 5];
	                    int len;

	                    while ((len = input.read(b)) != -1) {
	                        output.write(b, 0, len);
	                    }

	                    output.flush();
	                    output.close();
	                    input.close();
	                }

	                if (temp.isDirectory()) { //��������ļ���
	                    copyFolder(oldPath + "/" + file[i], newPath + "/" +
	                        file[i]);
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("��������ļ������ݲ�������");
	            e.printStackTrace();
	        }
	    }

	    /**
	       * �ƶ��ļ���ָ��Ŀ¼
	       * @param oldPath String �磺c:/fqf.txt
	       * @param newPath String �磺d:/fqf.txt
	       */
	    public static void moveFile(String oldPath, String newPath) {
	        copyFile(oldPath, newPath);
	        delFile(oldPath);
	    }

	    /**
	       * �ƶ��ļ���ָ��Ŀ¼
	       * @param oldPath String �磺c:/fqf.txt
	       * @param newPath String �磺d:/fqf.txt
	       */
	    public static void moveFolder(String oldPath, String newPath) {
	        copyFolder(oldPath, newPath);
	        delFolder(oldPath);
	    }
	    
	    public static long getFileSize(File f){
	    	long s = 0;
			if (f.exists()) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(f);
					s = fis.available();
				} catch (FileNotFoundException e) {
					s=-1;
				} catch (IOException e) {
					s=-1;
				}finally{
					if(fis!=null){
						try {
							fis.close();
							fis=null;
						} catch (IOException e) {}
					}
				}
				return s;
			} else {
				return -1;
			}
	    }


}
