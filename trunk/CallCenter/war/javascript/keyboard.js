var keyboardchange, usinglang_id, globalkey;

function kepressedevent(e) {
	var key = window.event ? e.keyCode : e.which;
	if (globalkey) {
		var altK = false;
		if (e.altKey)
			altK = true;
		var ctrlK = false;
		if (e.ctrlKey)
			ctrlK = true;
		var shiftK = false;
		if (e.shiftKey)
			shiftK = true;
		globalkey(altK, ctrlK, shiftK, key);
	}
	if (e.altKey || e.ctrlKey)
		return true;
	var f = e.target;
	if (!keyboardchange)
		return true;
	if (key == 96) {
		if (usinglang_id == 1)
			usinglang_id = 0;
		else
			usinglang_id = 1;
		return false;
	}
	if (usinglang_id == 0)
		return true;
	/*
	 * 
	 * 
	 * if (usinglang_id != 1) return true;
	 * 
	 * 
	 */

	var lang_id = 1;
	var ckey = keyboardchange(lang_id, key);
	if (!ckey)
		return true;
	if (ckey == key)
		return true;
	var g = f.selectionStart; // აქ ვიღევთ რამდენი სიმბოლო არის აკრეფილი
	// კურსორამდე
	f.value = f.value.substring(0, f.selectionStart) // კურსორამდე მთელი
	// ტექსტი
	+ String.fromCharCode(ckey) // დამატებული ახალი აკრეფილი სიმბოლო
	// (უკვე გადაყვანილი)
	+ f.value.substring(f.selectionEnd); // დამატებული კურსორის
	// მერე რაც წერია
	// f.value+=String.fromCharCode(geo[i]);
	g++;
	f.setSelectionRange(g, g);
	return false;
}
function registerglobal() {
	usinglang_id = 1;
	document.onkeypress = kepressedevent;
}

function setLanguage(lang){
	usinglang_id = lang;
}

function setkeyboardPress(elem, lang_id) {
	if (!elem)
		return;
	usinglang_id = 1;
	elem.setAttribute("lang_id", lang_id);
	elem.setAttribute("usinglang_id", 1);
	elem.onkeypress = kepressedevent;
}