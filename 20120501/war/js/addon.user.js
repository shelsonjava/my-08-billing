// ==UserScript==
// @name           sipml5 DTMF and Auto Answer 
// @namespace      sipml5
// @author         James Mortensen 
// @description    Add DTMF Codes to sipml5.org demo
// @include        http://*.sipml5.org/*
// @include        http://sipml5.org/*
// ==/UserScript==

function loadJS(fn) {
    var script = document.createElement("script");
    script.setAttribute("type","text/javascript");
    script.textContent = fn.toString();
    document.body.appendChild(script);
};
    
window.addEventListener("load", function() {
    loadDialpad();
    //loadAutoAnswer();
    loadJS(handleDialpadKeypress);
    loadJS(loadAutoAnswer + "; loadAutoAnswer();");
    loadJS(toggleAutoAnswer);
});

function loadAutoAnswer() {

    startRingTone = (function(legacyFn) {  
        
		/** arguments for original fn **/
		return function() { 
	    	    // original function pass in as parameter
		    legacyFn.apply(this, arguments);

                    if(window.localStorage.getItem("auto-answer-status") != "disabled") {
  	                setTimeout(function() {
                            //document.getElementById("btnCall").click();
                        },2000);
                    }	    		    	
	        };
	
    })(startRingTone);

    //var autoAnswerDiv = document.createElement("div");
    //autoAnswerDiv.setAttribute("style","display:table-cell; vertical-align:middle");
    //autoAnswerDiv.setAttribute("id","autoAnswerDiv");
    //autoAnswerDiv.setAttribute("class","span2 well");
    //document.getElementsByClassName("row-fluid")[0].appendChild(autoAnswerDiv);

    //var h2 = document.createElement("h2");
    //h2.innerHTML = "Auto Answer";
    //autoAnswerDiv.appendChild(h2);

    var autoAnswerStatus = 'enabled';

    //var label = document.createElement("label");
    //if(autoAnswerStatus == "disabled") {
     //   label.innerHTML = "<i>Auto answer: Disabled</i>";
    //} else {
     //   label.innerHTML = "<i>Auto answer: Enabled</i>";
    //}
    //label.setAttribute("style","width:100%;");
    //label.setAttribute("align","center");
    //label.setAttribute("id","autoAnswerStatus");
    
    //document.getElementById("autoAnswerDiv").appendChild(label);

    //var input = document.createElement("input");
    //input.setAttribute("type","button");
    //input.setAttribute("class","btn-primary");
    //input.setAttribute("style","width:100px;margin-left:30px");
    //input.setAttribute("value","Auto Answer");
    //input.setAttribute("onclick","toggleAutoAnswer()");
    
    //document.getElementById("autoAnswerDiv").appendChild(input);
    
}

function toggleAutoAnswer() {
    //var label = document.getElementById("autoAnswerStatus");
    //var autoAnswerStatus = window.localStorage.getItem("auto-answer-status");
    //if(autoAnswerStatus == "disabled") {
     //   window.localStorage.setItem("auto-answer-status","enabled");
     //   label.innerHTML = "<i>Auto answer: Enabled</i>";
    //} else {
     //   window.localStorage.setItem("auto-answer-status","disabled");
     //   label.innerHTML = "<i>Auto answer: Disabled</i>";
    //}
    
}

function loadDialpad() {
	/*
    console.info("sipml5dtmf");
    var dtmfDiv = document.createElement("div");
    dtmfDiv.setAttribute("style","display:table-cell; vertical-align:middle");
    dtmfDiv.setAttribute("id","dtmf");
    dtmfDiv.setAttribute("class","span2 well");
    document.getElementsByClassName("row-fluid")[0].appendChild(dtmfDiv);

    var h2 = document.createElement("h2"); 
    h2.innerHTML = "Dialpad";
    document.getElementById("dtmf").appendChild(h2);
    var br = document.createElement("br");
    document.getElementById("dtmf").appendChild(br);
   
    var table = document.createElement("table");
    var tbody = document.createElement("tbody");
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    var input;

    var digit;
    var last = ['*','0','#'];

    table.appendChild(tbody);

    for(var i = 0; i < 4; i++) {
        tr = document.createElement("tr");
        for(var j = 0; j < 3; j++) {
            td = document.createElement("td");
            input = document.createElement("input");
            input.setAttribute("type","button");
            input.setAttribute("class","btn-primary");
            input.setAttribute("style","width:25px;margin-right:10px");
            digit = i < 3 ? (3*i) + (j+1) : last[j];
            input.setAttribute("value", digit);
            input.setAttribute("onclick","handleDialpadKeypress('"+digit+"')");
            td.appendChild(input);
            tr.appendChild(td);
        }
        tr.appendChild(td);
        tbody.appendChild(tr);
    }

    dtmfDiv.appendChild(table);
    */

}

top.window.handleDialpadKeypress = handleDialpadKeypress;
function handleDialpadKeypress(value) {

    if(oSipSessionCall) { 

        // on a call, so send dtmf codes
        oSipSessionCall.dtmf(value);

    } else {

        // not in a call, so populate call control
        //document.getElementById("txtPhoneNumber").value += value;
        
    }
}

