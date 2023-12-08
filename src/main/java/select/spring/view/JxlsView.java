package select.spring.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.web.servlet.view.document.AbstractExcelView;

import select.spring.util.ApplicationProperty;
//
//public class JxlsView extends AbstractExcelView {
//
//	private String templatePath =ApplicationProperty.get("jxls.template.path");
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hhmmss");
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	protected void buildExcelDocument(Map<String, Object> model,
//			HSSFWorkbook workbook, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		// model에서 templateFileName 과 destFileName 을 받아서 설정한다.
//		String templateFileName = templatePath + model.get("templateFileName").toString();
//		String destFileName = model.get("destFileName").toString();
//		destFileName = URLEncoder.encode(destFileName, "UTF-8");
//
//		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(templateFileName);
//
//		XLSTransformer transformer = new XLSTransformer();
//		Workbook resultWorkbook = transformer.transformXLS(is, (Map)model.get("data"));
//
//		StringBuffer contentDisposition = new StringBuffer();
//		contentDisposition.append("attachment;fileName=\"");
//		contentDisposition.append(destFileName);
//		contentDisposition.append("\";");
//
//		response.setHeader("Content-Disposition", contentDisposition.toString());
//		response.setContentType("application/x-msexcel");
//		resultWorkbook.write(response.getOutputStream());
//	}
//}