const lang_change = document.getElementById("language-change");

function showDropdownContent() {
	document.getElementById("dropdown-content").classList.toggle("show");
}

lang_change.onclick = showDropdownContent;

window.onclick = function(event) {
	if (event.target.id != 'language-change') {
		var dropdowns = document.getElementsByClassName("dropdown-content");
		var i;
		for (i = 0; i < dropdowns.length; i++) {
			var openDropdown = dropdowns[i];
			if (openDropdown.classList.contains('show')) {
				openDropdown.classList.remove('show');
			}
		}
	}
}

var basketSizeElement = document.getElementById("basketSize");

function increaseBasket() {
	basketSizeElement.innerHTML = ++basketSizeElement.innerHTML;
}

(function initBasket() {
	let initBasketSize = 0;
	let storage = localStorage.getItem("drinks");
	if (storage) {
		let drinks = JSON.parse(storage);
		for (let element in drinks) {
			initBasketSize += parseInt(drinks[element].amount);
		}
	}

	basketSizeElement.innerHTML = initBasketSize;
})();