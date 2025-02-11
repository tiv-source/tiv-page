/**
 * 
 */
package de.tivsource.page.admin.actions.others.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.inject.Inject;




public class CkeditorUpload extends ActionSupport {

  private static final long   serialVersionUID      = 8163586984026522679L;
  private String              uploadContentType;
  private String              uploadFileName;
  private String              uploadFolder          = "/uploads";
  private String              CKEditorFuncNum;
  private String              CKEditor;
  private String              langCode;
  private File                upload;
  private boolean             allowUploads          = false;
  private String              allowedFileExtensions = "";

  private final static Logger LOG                   = LogManager.getLogger(CkeditorUpload.class);

  @Actions({
      @org.apache.struts2.convention.annotation.Action(
              value = "upload", 
              results = {@Result(name = "error", type="freemarker", location = "/ckeditor/ckeditorError.ftl")}
      )
  })
  @Override
  public String execute() throws Exception
  {

    if (LOG.isDebugEnabled())
    {
      LOG.debug("Upload is allowed : [#0]", this.allowUploads);
      LOG.debug("Allowed File Extensions : [#0]", this.allowedFileExtensions);
      LOG.debug("Upload Folder : [#0]", this.uploadFolder);
    }

    if (!allowUploads)
    {
      addActionError(getText("struts.ckeditor.error.notallowed", "Uploads are not allowed!"));
      return Action.ERROR;
    }

    //String strPath = ServletActionContext.getServletContext().getRealPath(uploadFolder);
    String strPath = "/var/www/html/uploads";
    File path = new File(strPath);
    if (!path.exists())
    {
      path.mkdirs();
    }
    String uuid = UUID.randomUUID().toString();
    String rt[] = uploadFileName.split("\\.");

    String uploadFileExtension = "";
    if (rt.length >= 2)
    {
      uploadFileExtension = rt[rt.length - 1];
      uploadFileName = new String(uuid + "." + uploadFileExtension);
    }

    boolean isAllowed = ArrayUtils.contains(allowedFileExtensions.split(","), uploadFileExtension.toLowerCase());

    if (LOG.isDebugEnabled())
    {
      LOG.debug("Upload Content Type : [#0]", this.uploadContentType);
      LOG.debug("Upload File Name : [#0]", uploadFileName);
      LOG.debug("Upload File Extension : [#0]", uploadFileExtension);
      LOG.debug("File Extension is allowed : [#0]", isAllowed);
    }

    if (!isAllowed)
    {
      addActionError(getText("struts.ckeditor.error.invalid.extension", "Invalid File Extension!"));
      return Action.ERROR;
    }

    InputStream is = new FileInputStream(this.upload);
    OutputStream os = new FileOutputStream(new File(strPath + File.separator + this.uploadFileName));
    try
    {
      int len;
      byte[] buffer = new byte[ 1024];
      while ((len = is.read(buffer)) > 0)
      {
        os.write(buffer, 0, len);
      }
    }
    catch (Exception e)
    {
      LOG.error(e.getLocalizedMessage(), e);
      addActionError(getText("struts.ckeditor.error.general", "Error occurred! " + e.getLocalizedMessage()));
      return Action.ERROR;
    }
    finally
    {
      if (is != null)
      {
        is.close();
      }
      if (os != null)
      {
        os.close();
      }
    }
    PrintWriter out = ServletActionContext.getResponse().getWriter();
    out.write("<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(" + this.CKEditorFuncNum + ", \"" + this.uploadFolder + "/" + this.uploadFileName + "\", \"\");</script>");
    return Action.NONE;
  }

  /**
   * @param uploadFolder
   *          folder for uploads. default is "/uploads".
   */
  @Inject(value = "struts.ckeditor.uploadFolder", required = false)
  public void setUploadFolder(String uploadFolder)
  {
    this.uploadFolder = uploadFolder;
  }

  /**
   * @param allowedFileExtensions
   *          allowed file extensions. default is "".
   */
  @Inject(value = "struts.ckeditor.allowedFileExtensions", required = false)
  public void setAllowedFileExtensions(String allowedFileExtensions)
  {
    this.allowedFileExtensions = allowedFileExtensions;
  }

  /**
   * @param allowUploads
   *          allow file uploads. default is false.
   */
  @Inject(value = "struts.ckeditor.allowUploads", required = false)
  public void setAllowUploads(String allowUploads)
  {
    this.allowUploads = allowUploads.equalsIgnoreCase("true");
  }

  /**
   * @return the upload
   */
  public File getUpload()
  {
    return upload;
  }

  /**
   * @param upload
   *          the upload to set
   */
  public void setUpload(File upload)
  {
    this.upload = upload;
  }

  /**
   * @return the cKEditorFuncNum
   */
  public String getCKEditorFuncNum()
  {
    return CKEditorFuncNum;
  }

  /**
   * @param cKEditorFuncNum
   *          the cKEditorFuncNum to set
   */
  public void setCKEditorFuncNum(String cKEditorFuncNum)
  {
    CKEditorFuncNum = cKEditorFuncNum;
  }

  /**
   * @return the cKEditor
   */
  public String getCKEditor()
  {
    return CKEditor;
  }

  /**
   * @param cKEditor
   *          the cKEditor to set
   */
  public void setCKEditor(String cKEditor)
  {
    CKEditor = cKEditor;
  }

  /**
   * @return the langCode
   */
  public String getLangCode()
  {
    return langCode;
  }

  /**
   * @param langCode
   *          the langCode to set
   */
  public void setLangCode(String langCode)
  {
    this.langCode = langCode;
  }

  /**
   * @return the uploadContentType
   */
  public String getUploadContentType()
  {
    return uploadContentType;
  }

  /**
   * @param uploadContentType
   *          the uploadContentType to set
   */
  public void setUploadContentType(String uploadContentType)
  {
    this.uploadContentType = uploadContentType;
  }

  /**
   * @return the uploadFileName
   */
  public String getUploadFileName()
  {
    return uploadFileName;
  }

  /**
   * @param uploadFileName
   *          the uploadFileName to set
   */
  public void setUploadFileName(String uploadFileName)
  {
    this.uploadFileName = uploadFileName;
  }
}
