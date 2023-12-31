package select.spring.util;

import org.apache.commons.io.*;
import org.apache.commons.io.FileUtils.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public class FileUtils {

	public static String saveUniqeName(String fileName, String path, byte[] data) {
    	
    	
    	String saveFileName = "";
    	FileOutputStream output = null;
		try {
			
			
			File file = new File(path + fileName);
			createDir(file.getPath());
			file = uniqueNamedFile(file);
			
	        output = new FileOutputStream(file);
	        output.write(data);   

	        output.close();
	        saveFileName = file.getName();
	        
		} catch (FileNotFoundException e) {
	        e.printStackTrace();
	        saveFileName = "";
	    } catch (IOException e) {
	        e.printStackTrace();
	        saveFileName = "";
	    } 
		
    	return saveFileName;
    }
 
	
    private static File uniqueNamedFile(File f) {
 	   
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	Random random = new Random();
    	
 	   //확장자가 없는 파일 일때 처리
		String name = f.getName();
		String body = null;
		String ext = null;

		int dot = name.lastIndexOf(".");
		if (dot != -1) {	//확장자가 존재
			body = name.substring(0, dot);
			ext = name.substring(dot);
		} else {			//확장자가 없는 경우
			body = name;
			ext = "";
		}

		String saveFileName = sdf.format(new Date()).toString() + random.nextInt(100)+ ext;
		f = new File(f.getParent(), saveFileName);
	
		return f;
    }


    
    /**
     * 파일을 업로드한다.
     * @param file 파일 Object
     * @param orgFileNm 원본 파일 명
     * @param path 저장할 파일 경로
     * @return 저장된 파일 명
     */
    public static String saveDateName(File file, String orgFileNm, String path) {
        // 사용자가 업로드한 파일
        if (file == null || "".equals(orgFileNm)) { return null; }

        // 파일_확장자
        if (orgFileNm.lastIndexOf(".") < 0) {
            return null;
        }
        String fileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".")).toLowerCase();

        // 서버에 저장할 새로운 파일명을 만든다.
        String fileNm = DateUtils.getToday("yyyyMMddHHmmss") + System.currentTimeMillis() + fileExt;

        FileUtils.saveForce(file, fileNm, path);

        return fileNm;
    }

    /**
     * 파일을 업로드한다.
     * @param file 파일 Object
     * @param orgFileNm 원본 파일 명
     * @param saveFileNm 저장 파일 명
     * @param path 저장할 파일 경로
     * @return 저장된 파일 명
     */
    public static String saveNewName(File file, String orgFileNm, String saveFileNm, String path) {
        // 사용자가 업로드한 파일
        if (file == null || "".equals(orgFileNm) || "".equals(saveFileNm)) { return null; }

        // 파일_확장자
        if (orgFileNm.lastIndexOf(".") < 0) {
            return null;
        }
        String orgFileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".")).toLowerCase();

        if (saveFileNm.lastIndexOf(".") < 0) {
            saveFileNm = saveFileNm + orgFileExt;
        }

        FileUtils.saveForce(file, saveFileNm, path);

        return saveFileNm;
    }

    /**
     * 파일 명 생성 없이 파일을 업로드한다.
     * @param file 파일 Object
     * @param fileNm 파일 명
     * @param path 저장할 파일 경로
     */
    public static void saveForce(File file, String fileNm, String path) {
        // 업로드할 경로가 존재하지 않는 경우 폴더 생성
        FileUtils.createDir(new File(path));

        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(path + fileNm);
            int bytesRead = 0;
            int bufferSize = 1024 * 8;
            byte[] buffer = new byte[bufferSize];
            while ((bytesRead = fis.read(buffer, 0, bufferSize)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 파일을 삭제한다.
     * @param fileNm 파일_명
     * @return 파일 삭제 성공 여부
     */
    public static boolean remove(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public static boolean remove(String fileName, String path) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 파일을 복사한다.
     * @param source 원본 파일
     * @param target 복사 파일
     * @return 파일 복사 성공 여부
     */
    @SuppressWarnings("finally")
    public static boolean copy(File source, String target) {
        boolean copyAt = false;

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(source));
            bos = new BufferedOutputStream(new FileOutputStream(target));
            int byteNumer;
            try {
                while ((byteNumer = bis.read()) != -1) {
                    bos.write(byteNumer);
                }
                copyAt = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return copyAt;
            }
        }
    }

    /**
     * 파일을 복사한다.
     * @param source 원본 파일 명
     * @param target 복사 파일 명
     * @return 파일 복사 성공 여부
     */
    public static boolean copy(String source, String target) {
        File sourceFile = new File(source);
        // File targetFile = new File(target);

        // if (sourceFile.exists() && targetFile.exists()) {
        if (sourceFile.exists()) {
            return FileUtils.copy(sourceFile, target);
        } else {
            return false;
        }
    }

    /**
     * 파일을 이동한다.
     * @param source 원본 파일 명
     * @param target 복사 파일 명
     * @return 파일 이동 성공 여부
     */
    public static boolean move(String source, String target) {
        File sourceFile = new File(source);

        if (sourceFile.exists()) {
            // 복사할 저장 디렉토리 경로가 없으면 디렉토리를 생성한다.
            FileUtils.createDir(target);

            // 파일 복사 유틸리티를 이용하여 복사한다.
            // 복사 후 기존 파일은 삭제한다.
            if (FileUtils.copy(source, target)) {
                sourceFile.delete();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        
    }

    /**
     * 디렉토리가 존재하지 않을 시 생성한다.
     * @param path 디렉토리 경로
     */
    public static void createDir(File path) {
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * 디렉토리가 존재하지 않을 시 생성한다.
     * @param fileNm 파일 명 (디렉토리 포함)
     */
    public static void createDir(String fileNm) {
        if (fileNm.indexOf(File.separator) > 0) {
            String path = fileNm.substring(0, fileNm.lastIndexOf(File.separator));
            FileUtils.createDir(new File(path));
        }
    }
    
}
