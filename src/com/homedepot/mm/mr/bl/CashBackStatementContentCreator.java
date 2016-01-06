package com.homedepot.mm.mr.bl;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.homedepot.mm.mr.dto.CashBackStatementDTO;
import com.homedepot.mm.mr.recognition.exception.ProRecognitionException;
import com.homedepot.mm.mr.recognition.utility.PDFUtil;
import com.homedepot.mm.mr.recognition.utility.freemarker.FreemarkerManager;
import com.homedepot.mm.mr.utility.Constants;
import com.homedepot.mm.mr.utility.Util;
import com.homedepot.ta.aa.log4j.OrangeLogHelper;
import com.lowagie.text.DocumentException;

import freemarker.template.TemplateException;

public final class CashBackStatementContentCreator {

	private static final Logger LOGGER = Logger.getLogger(OrangeLogHelper.getLoggerNameUsingTomcatClassLoaderAndTheStack());

	/**
	 * Hide the no-arg constructor
	 */
	private CashBackStatementContentCreator() {
		super();
	}


/**
 * createCashBackStatement
 * @param request
 * @return
 * @throws TemplateException
 * @throws IOException
 * @throws ClassNotFoundException
 * @throws ParserConfigurationException
 * @throws SAXException
 * @throws DocumentException
 * @throws ProRecognitionException 
 */
	public static byte[]  createCashBackStatement(CashBackStatementDTO request)
			throws TemplateException, IOException,  ClassNotFoundException, ParserConfigurationException, SAXException, DocumentException, ProRecognitionException {
		
		LOGGER.debug("In method createCashBackStatement ");
		
		byte[] pdf = null;
		
		//Create the substitution variables
		Map<String, Object> substitutionMap = createSubstitionMap(request);

		// Get html templates
		String templateFileUrl = Util.getBaseURL() + Constants.templateURL;
		String secondaryTemplateFileUrl = Util.getBaseURL() + Constants.templateURL;

		// Generate html Document
		String htmlFileContent = createHtmlFileContent(substitutionMap, templateFileUrl, secondaryTemplateFileUrl);
		
		// Generate PDF
		try {
			pdf = PDFUtil.generatePDF(htmlFileContent);
			
		} catch (DocumentException e) {
			LOGGER.debug("DocumentException:  "+ e.getStackTrace());
			throw new ProRecognitionException(6001);

		}

		return pdf;
	}


	/**
	 * createSubstitionMap
	 * @param request
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static Map<String, Object> createSubstitionMap (CashBackStatementDTO request) throws ClassNotFoundException {
		
		LOGGER.debug("In method createSubstitionMap ");

		//Initialize the map
		Map<String, Object> substitutionMap = new HashMap<String, Object> ();
			
		//Variables for PRO LOGO and Header Details.
	    substitutionMap.put(Constants.THD_PRO_LOGO, Util.getBaseURL() + Constants.proXtralogoUrl);
		substitutionMap.put(Constants.CALENDER_QUARTER, request.getQuarterSummary().getTotalsDateRange());
		substitutionMap.put(Constants.SPEND, request.getQuarterSummary().getSpend());
		substitutionMap.put(Constants.CASH_BACK_LEVEL, request.getMembershipLevel().getDiscountPercentDisplay());
		substitutionMap.put(Constants.CASH_BACK_EARNED, request.getQuarterSummary().getCashBackEarned());
		//Transaction list
		substitutionMap.put(Constants.CUSTOMER_TRANSACTION_LIST, request.getTransactions());
			
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Substitution Map Created");
		}
		
		return substitutionMap;
	}	
	
	
	
	/**
	 * 
	 * @param substitutionMap
	 * @param templateFilePath
	 * @param secTemplateFilePath
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	protected static String createHtmlFileContent( Map<String, Object> substitutionMap, 
			String templateFilePath, String secTemplateFilePath) throws TemplateException, IOException {

		LOGGER.debug("In method createHtmlFileContent ");

		return FreemarkerManager.generateContent(substitutionMap,
				templateFilePath, secTemplateFilePath,
				FreemarkerManager.getUrlLoaderConfiguration());
	}
}
