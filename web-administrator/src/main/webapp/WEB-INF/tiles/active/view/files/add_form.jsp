<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2>Add User</h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/user" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >

            <fieldset class="fieldset">

              <div class="field">
                <sj:textfield 
                    key="user.username"
                    labelposition="left"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="user.email"
                    labelposition="left"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="user.firstname"
                    labelposition="left"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="user.lastname"
                    labelposition="left"
                    parentTheme="xhtml"
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
                    list="roleList" 
                    listKey="uuid"  
                    listValue="technical" 
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em; width: 640px;" 
					multiple="true" 
                />
                <script type="text/javascript">
                $("#add_user_roles").chosen()
                </script>
                
              </div>


            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">Save</button>

              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/user">Close</struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      
