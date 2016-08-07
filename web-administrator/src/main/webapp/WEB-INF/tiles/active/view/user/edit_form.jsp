<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="user.edit"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/user" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="user.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <sj:textfield 
                    key="user.username"
                    labelposition="left"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="user.email"
                    labelposition="left"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="user.firstname"
                    labelposition="left"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="user.lastname"
                    labelposition="left"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <struts:password 
                    key="user.password"
                    labelposition="left"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <struts:select
                    key="user.roles"
                    value="%{user.roles.{uuid}}"
                    list="roleList" 
                    listKey="uuid"  
                    listValue="technical" 
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em; width: 640px;" 
					multiple="true" 
                />
                <script type="text/javascript">
                $("#edit_user_roles").chosen()
                </script>
                
              </div>


            </fieldset>


            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.save"/>
              </button>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/user">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->
