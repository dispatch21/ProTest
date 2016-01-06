package com.homedepot.mm.mr.probe;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.homedepot.mm.mr.recognition.exception.RecognitionApplLogMessage;
import com.homedepot.mm.mr.recognition.utility.RAPUtil;
import com.homedepot.ta.aa.log4j.OrangeLogHelper;

/**
 * Checks the major components of an application and saves the result here. This
 * class is accessed by the ApplicationProbe.jsp to display the monitoring
 * results,
 * 
 * Application teams need to customize portions of this class for application
 * specific monitoring.
 * 
 */
public class ProbeResult
{

	private static final Logger LOGGER = Logger.getLogger(OrangeLogHelper.getLoggerNameUsingTomcatClassLoaderAndTheStack());

	private static final String APPLICATION_NAME = "PRO TRANSACTIONS";
	private static final String SYSTEM = "mm";
	private static final String SUBSYSTEM = "mr";

	/** Change These 2 Variables Before Building */
	private static final String APP_VERSION = "1.0";
	private static final String BUILD_DATE = "09-September-2015";
	
	private String RETURN_MESSAGE_PRO_TRANSACTIONS_ERROR = "Thrown from Pro Transactions - Page cannot be found";
	private int PRO_TRANSACTIONS_MESSAGE_NO = 404;

	private static final String UP = "<font size='+4' face='Arial, sans-serif' color='green'>Up </font>";
	private static final String DOWN = "<font size='+4' face='Arial, sans-serif' color='Red'>Down </font>";
	private String _result;
	private String _applicationName;
	private String _system;
	private String _subSystem;
	private String _appVersion;
	private String _buildDate;
	private ArrayList<String> _messages = new ArrayList<String>();

	/*
	 * Constructor
	 */
	public ProbeResult() 
	{
		_applicationName = APPLICATION_NAME;
		_system = SYSTEM;
		_subSystem = SUBSYSTEM;
		_appVersion = APP_VERSION;
		_buildDate = BUILD_DATE;
	}

	public String getApplicationName()
	{
		return _applicationName;
	}

	public ArrayList<String> getMessages()
	{
		return _messages;
	}

	/**
	 * Checks if the application components are working fine.
	 * 
	 * @return String "UP" if everything is fine, or "DOWN" if any application
	 *         component returned failure.
	 */
	public String getResult()
	{
		_messages.clear();

		try
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Inside getResult()");
			}
		}
		catch (Exception e)
		{
			StringBuffer errorMsg = new StringBuffer();
			errorMsg.append(RETURN_MESSAGE_PRO_TRANSACTIONS_ERROR);
			errorMsg.append("at time :: ");
			errorMsg.append(RAPUtil.getCurrentTimestamp().toString());
			errorMsg.append(e.toString());
			RecognitionApplLogMessage recognitionApplLogMsg = new RecognitionApplLogMessage(PRO_TRANSACTIONS_MESSAGE_NO,
					errorMsg.toString());
			LOGGER.fatal(recognitionApplLogMsg, e);

			LOGGER.debug(" Exception " + e.getMessage());
			_messages.add(e.getMessage());
		}
		if (_messages.size() == 0)
		{
			_result = UP;
		}
		else
		{
			_result = DOWN;
		}
		
		return _result;
	}

	public String getSubSystem()
	{
		return _subSystem;
	}

	public String getSystem()
	{
		return _system;
	}

	public String getAppVersion()
	{
		return _appVersion;
	}

	public String get_buildDate()
	{
		return _buildDate;
	}

	public void set_buildDate(String _buildDate)
	{
		this._buildDate = _buildDate;
	}

}