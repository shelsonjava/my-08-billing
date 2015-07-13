<!-- onload="onLoad()" onbeforeunload="onBeforeUnload()" -->
<audio id="ringing" loop src="audio/ringing.wav" />
	 	
	 		
        <script src="javascript/jain-sip.js" type="text/javascript"></script>
        <script src="javascript/MobicentsWebRTCPhone.js" type="text/javascript" ></script>
		<script type='text/javascript'>	
	            var mobicentsWebRTCPhone=null;         
	            var defaultStunServer="";
	            var defaultSipDomain="11808.ge";
	            var defaultSipDisplayName="Test";
	            var defaultSipUserName="test";
	            var defaultSipLogin="test@11808.ge";
	            var defaultSipPassword="test";
	            var defaultSipContactPhoneNumber="test@11808.ge";
	            var is_chrome=null;
	            
	            var localAudioVideoMediaStream=null;
	            
	            function onLoad()
	            {	            	
	                console.debug("onLoad");
	                is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
	                if(!is_chrome){
	                	return;
	                }
	                navigator.webkitGetUserMedia({audio:true, video:true},gotLocalAudioVideoStream, gotLocalAudioVideoFailed);
	                console.debug("onLoad.");
	            }
	
	            function onBeforeUnload()
	            {	
	            	if(!is_chrome){
	                	return;
	                }
	                unRegister();
	                for(var i=0;i<5000;i++)
	                {
	                    console.log("OnBeforeUnLoad()");  
	                }     
	            }
	            
	            function gotLocalAudioVideoStream (localStream) {
	                localAudioVideoMediaStream=localStream;
	                var url = webkitURL.createObjectURL(localStream);
	                //document.getElementById("localVideoPreview").src=url;
	                //document.getElementById("localVideoPreview").play();
	                showRegisterButton();
	            }
	
	            function  gotLocalAudioVideoFailed(error) 
	            {
	                alert("Failed to get access to local media. Error code was " + error.code + ".");
	                hideRegisterButton();
	            }	
	    
	            function register(sipDisplayName,sipUserName,sipLogin,sipPassword)
	            {		      
	            	
	            	if(!is_chrome){
	                	return;
	                }
	            	
	            	defaultSipDisplayName=sipDisplayName;
	            	defaultSipUserName=sipUserName;
	            	defaultSipLogin=sipLogin;
	            	defaultSipPassword=sipPassword;
	            	
	            	// enable notifications if not already done		
				    if (window.webkitNotifications) {
				    	window.webkitNotifications.requestPermission();
				    }
				    mobicentsWebRTCPhone = new MobicentsWebRTCPhone("ws://192.168.1.7:5062");
	                mobicentsWebRTCPhone.localAudioVideoMediaStream=localAudioVideoMediaStream;	                
	            }
	
	            function unRegister()
	            {
	            	if(!is_chrome){
	                	return;
	                }
	                if(mobicentsWebRTCPhone!=null)
	                {
	                    mobicentsWebRTCPhone.unRegister();   
	                }
	            }
	
	            function call(from,to)
	            {   
	                mobicentsWebRTCPhone.call(from,to);
	            }
	
	
	            function bye()
	            {
	               // mobicentsWebRTCPhone.bye();
	            }
	                
	                
	            function hideCallButton()
	            {
	             //   var call=document.getElementById("Call");
	              //  call.disabled=true;
	            }
	            
	            function showCallButton()
	            {
	               // var call=document.getElementById("Call");
	              //  call.disabled=false;
	            }
	            
	            function hideByeButton()
	            {
	              //  var bye=document.getElementById("Bye");
	              //  bye.disabled=true;
	            }
	            
	            function showByeButton()
	            {
	              //  var bye=document.getElementById("Bye");
	              //  bye.disabled=false;
	            }
	            
	            function showUnRegisterButton()
	            {
	               // var unRegister=document.getElementById("UnRegister");
	                //unRegister.disabled=false;
	            }
	            
	            function showRegisterButton()
	            {
	                //var register=document.getElementById("Register");
	                //register.disabled=false;
	            }
	            function hideUnRegisterButton()
	            {
	                //var unRegister=document.getElementById("UnRegister");
	                //unRegister.disabled=true;
	            }
	            
	            function hideRegisterButton()
	            {
	                //var register=document.getElementById("Register");
	                //register.disabled=true;
	            }	
			
		    function startRinging() {
	        	ringing.play(); 
		    }
	
		    function stopRinging() {
				ringing.pause();
		    }
		    
		    var myCallBack;
		    
	     </script>