function removeIngredient() {
	this.event.target.parentNode.parentNode.remove();
}

var iterator = 1;
const divForCopy = document.getElementById("copy");
const ingredients = document.getElementById("ingredientsList");
const addIngredientBtn = document.getElementById("add");

function addIngredient() {
	var div = document.createElement('div');
	div.classList.add("flex-display");
	div.innerHTML = divForCopy.innerHTML;
	var divs = div.getElementsByTagName('div');
	let elemId;

	elemId = 'ingr' + iterator;
	divs[0].getElementsByTagName('select')[0].id = elemId;
	divs[0].getElementsByTagName('label')[0].htmlFor = elemId;

	elemId = 'amount' + iterator;
	divs[1].getElementsByTagName('input')[0].id = elemId;
	divs[1].getElementsByTagName('label')[0].htmlFor = elemId;

	elemId = 'option' + iterator;
	divs[2].getElementsByTagName('select')[0].id = elemId;
	divs[2].getElementsByTagName('label')[0].htmlFor = elemId;

	ingredients.appendChild(div);
	
	++iterator;
}

addIngredientBtn.onclick = addIngredient;