<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <h1>Backhaus Bahnhofstraße</h1>
    
    <div class="location1_1">
      
      <div class="adress">
        <h5>Anschrift:</h5>
        <p>Bahnhofstrasse 3</p>
        <p>58452 Witten</p>
      </div>

      <div class="location_map">
        <picture>
				<source media="(min-width:1701px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1701.png">
				<source media="(min-width:1501px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1501.png">
				<source media="(min-width:1401px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1401.png">
				<source media="(min-width:1321px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1321.png">
				<source media="(min-width:1291px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1291.png">
				<source media="(min-width:1101px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1101.png">
				<source media="(min-width:1001px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1001.png">
				<source media="(min-width: 951px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0951.png">
				<source media="(min-width: 901px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0901.png">
				<source media="(min-width: 701px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0701.png">
				<source media="(min-width: 641px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0641.png">
				<source media="(min-width: 619px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0619.png">	
				<source media="(min-width: 581px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0581.png">
				<source media="(min-width: 201px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0201.png">
				
				<!-- Fallback -->
				<img src="/osmcache/<struts:property value="location.uuid" />_w0201.png" 
					srcset="/osmcache/<struts:property value="location.uuid" />_w0201.png">
			</picture>
		</div>
        
		<h5 class="distance_top3">Kontakt:</h5>
        
		<p><img alt="Telephone" src="locations-Dateien/telephone.png" style="width: 20px; height: 20px; float: left; margin-bottom: 2px;">&nbsp;&nbsp; 02302 - 424084</p>
         
		<p><img alt="Mobile" class="icon" src="locations-Dateien/mobile.png" style="width: 10px; height: 20px; padding:0 5px;float: left;">&nbsp;&nbsp; 0164 - </p>
    		
		<p><img alt="Printer" class="icon" src="locations-Dateien/fax.png" style="width: 20px; height: 20px; float: left;">&nbsp;&nbsp; 02302 - 424105</p>
    		
		<p><img alt="E-Mail" class="icon" src="locations-Dateien/email.png" style="width: 20px; height: 20px; float: left;">&nbsp;&nbsp; info@backhaus-24.de</p>
    		
		<p><img alt="World" class="icon" src="locations-Dateien/world.png" style="width: 20px; height: 20px; float: left;">&nbsp;&nbsp; http://www.backhaus-24.de</p>
    			 

    	<div class="location_opening">
    	    
          <h5>Öffnungszeiten:</h5>

          <table>
            <colgroup>
              <col width="110">
              <col width="">
            </colgroup>
            
            <tbody><tr>
              <td>Montag:</td>
              <td>06:00-19:00</td>
            </tr>
            
            <tr>
              <td>Dienstag:</td>
              <td>06:00-19:00</td>
            </tr>
            
            <tr>
              <td>Mittwoch:</td>
              <td>06:00-19:00</td>
            </tr>
            
            <tr>
              <td>Donnerstag:</td>
              <td>06:00-19:00</td>
            </tr>
            
            <tr>
              <td>Freitag:</td>
              <td>06:00-19:00</td>
            </tr>
            
            <tr>
              <td>Samstag:</td>
              <td>06:00-16:00</td>
            </tr>
            
            <tr>
              <td>Sonntag:</td>
              <td>09:00-17:00</td>
            </tr>
            
          </tbody></table>
          
		</div>
        
    </div>
     
     <hr>