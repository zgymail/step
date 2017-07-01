/**
 * 
 */
package hr.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author Gavin Yu
 * 
 */
public class StoreUtil {
	public static byte[] getByteArray(InputStream stream) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	        BufferedInputStream in = null;  
	        try{  
	            in = new BufferedInputStream(stream);  
	            int buf_size = 1024;  
	            byte[] buffer = new byte[buf_size];  
	            int len = 0;  
	            while(-1 != (len = in.read(buffer,0,buf_size))){  
	                bos.write(buffer,0,len);  
	            }  
	            return bos.toByteArray();  
	        }catch (IOException e) {  
	            e.printStackTrace();  
	            throw e;  
	        }finally{  
	            try{  
	                in.close();  
	            }catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            bos.close();  
	        }  
	}

	public static byte[] getByteArray(String filePath) throws IOException {
		FileInputStream obj = new FileInputStream(filePath);
		byte[] re = getByteArray(obj);
		obj.close();
		return re;
	}

	public static byte[] getByteArray(File file) throws IOException {
		FileInputStream is = new FileInputStream(file);
		byte[] re = getByteArray(is);
		is.close();
		return re;
	}

	public static void writerByteArray(OutputStream stream, byte[] sendFile)
			throws IOException {
		if (sendFile == null) {
			throw new IOException("send object is null");
		}

		stream.write(sendFile);
	}

	public static void writeByteArray(File file, byte[] sendFile)
			throws IOException {
		FileOutputStream os = new FileOutputStream(file);
		writerByteArray(os, sendFile);
		os.close();
	}

	public static void writeByteArray(String filePath, byte[] sendFile)
			throws IOException {
		FileOutputStream os = new FileOutputStream(filePath);
		writerByteArray(os, sendFile);
		os.close();
	}

	public static void writerByteArray(OutputStream outputStream, InputStream inputStream)
			throws IOException {
		if (inputStream == null) {
			throw new IOException("send object is null");
		}

		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
		
		int i = 0;
		byte[] buffer = new byte[512];
		while(true) {
			if(bufferedInputStream.available() < 512) {
				while(i != -1) {
					i = bufferedInputStream.read();
					bufferedOutputStream.write(i);
				}
				break;
			} else {
				bufferedInputStream.read(buffer);
				bufferedOutputStream.write(buffer);
			}
		}
		bufferedOutputStream.flush();
		bufferedInputStream.close();
		bufferedOutputStream.close();


	}

	public static void writeByteArray(File file, InputStream sendFile)
			throws IOException {
		FileOutputStream os = new FileOutputStream(file);
		writerByteArray(os, sendFile);
		os.close();
	}

	public static void writeByteArray(String filePath, InputStream sendFile)
			throws IOException {
		FileOutputStream os = new FileOutputStream(filePath);
		writerByteArray(os, sendFile);
		os.close();
	}

	public static void writeString(File file, String string,String enCode) throws IOException {
		FileOutputStream fos=null;
		OutputStreamWriter osw=null;
		BufferedWriter bw =null;
		try{
		fos=new FileOutputStream(file);
		osw=new OutputStreamWriter(fos, enCode);
		bw = new BufferedWriter(osw);
		bw.write(string);
		bw.flush();
		}finally{
			if(bw!=null){
				bw.close();
			}
			if(osw!=null){
				osw.close();
			}
			if(fos!=null){
				fos.close();
			}
		
		
		
		}
	}
	
	public static void writeString(File filePath, String string)
			throws IOException {
		writeString(filePath, string,"UTF-8");
	}

	public static void writeString(String filePath, String string)
			throws IOException {
		writeString(new File(filePath), string,"UTF-8");
	}
	
	public static void writeString(String filePath, String string,String enCode)
	throws IOException {
		writeString(new File(filePath), string,enCode);
	}
	
	
	public static void writeDesString(String filePath, String string)
			throws IOException {
		writeString(filePath+".data",string);
		byte[] enbyte = string.getBytes("UTF-8");
		try {
			enbyte=DesUtil.encryptDES(enbyte);
			DataOutputStream d = new DataOutputStream(new FileOutputStream(filePath));
			d.write(enbyte);
			d.flush();
			d.close();
		} catch (CodingException e) {
			throw new IOException(e);
		}
		
	}
	
	

	public static String getString(InputStream is) throws IOException {
		String sLine;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			int n = 0;
			while ((sLine = br.readLine()) != null) {
				if (n > 0) {
					sb.append("\r\n");
				}
				sb.append(sLine);
				n++;
			}
			return sb.toString();
		} finally {
			if (br != null) {
				br.close();
				br = null;
			}
		}
	}
	public static String getString(File file) throws IOException {
		return getString(file,"UTF-8");
		
	}

	public static String getString(File file,String enCode) throws IOException {
		String sLine;
		FileReader reader = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), enCode));

			StringBuffer sb = new StringBuffer();
			int n = 0;
			while ((sLine = br.readLine()) != null) {
				if (n > 0) {
					sb.append("\r\n");
				}
				sb.append(sLine);
				n++;
			}
			return sb.toString();
		} finally {
			if (br != null) {
				br.close();
				br = null;
			}
			if (reader != null) {
				reader.close();
				reader = null;
			}
		}
	}

	public static String getString(String file) throws IOException {
		File f = new File(file);
		if (!f.exists()) {
			return "";
		} else {
			return getString(new File(file));
		}
	}
	
	public static String getDesString(String file) throws IOException {
		byte[] data=getByteArray(file);
		try {
			data=DesUtil.decryptDES(data);
			String string=new String(data,"UTF-8");
			return string;
		} catch (CodingException e) {
			throw new IOException(e);
		}
		
	}
	public static URL getUrl(String path) {
		return Thread.currentThread().getContextClassLoader().getResource(path);
	}

	public static InputStream getClassInputStream(String path) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);
	}
	
	public static InputStream getInputStream(File path) throws FileNotFoundException {
		 return new FileInputStream(path);
	}

	public static Object getSerializableObject(byte[] data)
			throws ClassNotFoundException, IOException {

		Object re = null;
		ObjectInputStream objInputStream = null;
		ByteArrayInputStream byteInputStream = null;
		try {
			byteInputStream = new ByteArrayInputStream(data);
			objInputStream = new ObjectInputStream(byteInputStream);
			if (byteInputStream.available() > 0) {
				re = objInputStream.readObject();
			}
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (objInputStream != null) {
				objInputStream.close();
			}
			if (byteInputStream != null) {
				byteInputStream.close();
			}
		}

		return re;
	}

	public static Object getSerializableObject(InputStream in)
			throws ClassNotFoundException, IOException {
		Object re = null;
		ObjectInputStream objInputStream = null;
		try {
			objInputStream = new ObjectInputStream(in);
			re = objInputStream.readObject();
			
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (objInputStream != null) {
				objInputStream.close();
			}

		}
        return re;
	}

	public static byte[] getByteArray(Object infoBean) throws IOException {
		ObjectOutputStream objOutputStream = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			objOutputStream = new ObjectOutputStream(bos);
			objOutputStream.writeObject(infoBean);
			return bos.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			if (objOutputStream != null) {
				objOutputStream.close();
				objOutputStream = null;
			}
			if (bos != null) {
				bos.close();
				bos = null;
			}
		}
   }
	
	public static void writerObject(File f,Object infoBean) throws IOException {
		ObjectOutputStream objOutputStream = null;
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(f);
			objOutputStream = new ObjectOutputStream(fs);
			objOutputStream.writeObject(infoBean);
			fs.flush();
			
		} catch (IOException e) {
			throw e;
		} finally {
			if (objOutputStream != null) {
				objOutputStream.close();
				objOutputStream = null;
			}
			if (fs != null) {
				fs.close();
				fs = null;
			}
		}
   }
	
	
	/**
	 * Ŀ¼����
	 * 
	 * @param soruce
	 * @param target
	 * @throws IOException
	 */
	public static void copyDirectory(String soruce, String target)
			throws IOException {
		File sf = new File(soruce);
		if (!sf.exists()) {
			sf.mkdir();
		}
		File[] file = file = sf.listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				FileInputStream input = new FileInputStream(file[i]);
				FileOutputStream output = new FileOutputStream(target + "/"
						+ file[i].getName());

				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if (file[i].isDirectory()) {
				copyDirectory(target + "/" + file[i].getName(), soruce + "/"
						+ file[i].getName());
			}
		}
	}
	

	

	public static void copyFileToDirectory(String soruceFile, String targetDir)
			throws IOException {
		File file = new File(soruceFile);
		copyFile(soruceFile, targetDir + File.separator + file.getName());
	}

	public static void copyFile(String soruceFile, String targetFile)
			throws IOException {
		File file = new File(soruceFile);
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			FileInputStream input = null;
			FileOutputStream output = null;
			try {
				input = new FileInputStream(file);
				File outFile = new File(targetFile);

				if (file.equals(outFile)) {
					return;
				}
				File p = outFile.getParentFile();
				if(!p.exists()){
				    p.mkdirs(); 
				}
				output = new FileOutputStream(outFile);

				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
			} finally {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			}
		}
	}

	public static ConfigStore getConfigStore() {
		return new ConfigStore();
	}

	public static ConfigStore getConfigStore(String local) {
		return new ConfigStore(local);
	}

	public static void outZip(String file, String baseDir) throws Exception {
		OutputStream os = null;
		InputStream is = null;
		try {

			ZipFile zfile = new ZipFile(file);
			Enumeration zList = zfile.entries();
			ZipEntry ze = null;
			byte[] buf = new byte[1024 * 5];

			int n = 0;
			while (zList.hasMoreElements()) {
				ze = (ZipEntry) zList.nextElement();
				if (ze.isDirectory()) {
					continue;
				}
				os = new BufferedOutputStream(new FileOutputStream(
						getRealFileName(baseDir, ze.getName())));
				is = new BufferedInputStream(zfile.getInputStream(ze));
				int readLen = 0;
				while ((readLen = is.read(buf, 0, 1024 * 5)) != -1) {
					os.write(buf, 0, readLen);
				}
				os.flush();
				n++;
			}
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
			if (os != null) {
				os.close();
				os = null;
			}
		}
	}

	private static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {
			ret.mkdirs();
		}
		ret = new File(ret, dirs[dirs.length - 1]);
		return ret;
	}

}
