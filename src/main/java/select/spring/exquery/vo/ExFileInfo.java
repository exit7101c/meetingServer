package select.spring.exquery.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class ExFileInfo {

	public static int NEW_INIT 	 = 1;	// 최초
	public static int NEW_MODIFIED  = 2;	// 신규등록
	public static int NOT_MODIFIED  = 3;	// 조회, 변경없음
	public static int DATA_MODIFIED = 4;	// 파일 변경
	public static int DATA_DELETED  = 5;	// 파일 삭제
	
	public int state = NEW_INIT;
	
	public int   fileId = 0;
	public String fileType = "";
	public String fileName = "";
	public int   fileSize = 0;
	public byte fileData[];
	public boolean isDir;		// file browse & download시에 사용
	
	public int   orgFileId = 0;
	public String orgFileType = "";
	public String orgFileName = "";
	public int   orgFileSize = 0;
	
	public String bizName = "";

	public ExFileInfo() {
		
	}

	public ExFileInfo(HashMap<String, Object> param) {
		setAllFileInfo(param);
	}

	public ExFileInfo(String name, int size, Boolean isDir) {
		this.fileName = name;
		this.fileSize = size;
		this.isDir = isDir;
	}
	
	public void setOrgFileInfo(HashMap<String, Object> param) {
		this.state = NOT_MODIFIED;
		this.orgFileId = Integer.parseInt((String)param.get("fileId"));
		this.orgFileName = (String)param.get("orgFileName");
		this.orgFileType = (String)param.get("fileType");
		this.orgFileSize = Integer.parseInt((String)param.get("fileSize"));
		this.fileName = (String)param.get("saveFileName");
	}

	public void setNewFileInfo(HashMap<String, Object> param) {
		this.fileId = Integer.parseInt((String)param.get("fileId"));
		this.fileName = (String)param.get("savefileName");
		this.fileType = (String)param.get("fileType");
		this.fileSize = Integer.parseInt((String)param.get("fileSize"));
	}

	public void loadFileData(String path) {
		
		File file = new File(path + File.separator + fileName);
		FileInputStream input = null;
		
		try {
			input = new FileInputStream(file);
			this.fileData = new byte[(int)file.length()];
			input.read(this.fileData);
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}
	
	public void setAllFileInfo(HashMap<String, Object> param) {
		this.state = (Integer)param.get("state");
		this.orgFileId = (Integer)param.get("orgFileId");
		this.orgFileName = (String)param.get("orgFileName");
		this.orgFileType = (String)param.get("orgFileType");
		this.orgFileSize = (Integer)param.get("orgFileSize");
		this.fileId = (Integer)param.get("fileId");
		this.fileName = (String)param.get("fileName");
		this.fileType = (String)param.get("fileType");
		this.fileSize = (Integer)param.get("fileSize");
		this.fileData = (byte[])param.get("fileData");

		/*
		this.state = Integer.parseInt((String)param.get("state"));
		this.orgFileId = Long.parseLong((String)param.get("orgFileId"));
		this.orgFileName = (String)param.get("orgFileName");
		this.orgFileType = (String)param.get("orgFileType");
		this.orgFileSize = Long.parseLong((String)param.get("orgFileSize"));
		this.fileId = Long.parseLong((String)param.get("fileId"));
		this.fileName = (String)param.get("fileName");
		this.fileType = (String)param.get("fileType");
		this.fileSize = Long.parseLong((String)param.get("fileSize"));
		this.fileData = (byte[])param.get("fileData");
		*/
	}
	
}
