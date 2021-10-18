(function showDrinks() {
	let storage = localStorage.getItem("drinks");

	if (!storage || storage.length < 1) {
		let table = document.getElementsByTagName("table")[0];
		table.remove();
		/*let parent = table.parentNode;
		let img = document.createElement("img");
		img.src = "/CoffeeMachine/images/no_coffee.jpg";
		parent.appendChild(img);*/
		document.getElementById("emptyBasket").classList.remove("hide");
	} else {
		let drinks = JSON.parse(storage);
		let parent = document.getElementsByTagName("tbody")[0];
		let total = 0;
		for (element in drinks) {
			let drink = drinks[element];

			let price = drink.price;
			let showPrice = retrievePriceForPrint(price);

			let amount = drink.amount;
			let sum = price * amount;
			total += sum;
			let showSum = retrievePriceForPrint(sum);

			let tr = document.createElement("tr");
			tr.classList.add("basket-list-tr");
			tr.innerHTML = `<td><img class="basket-list-image"
								src="${drink.image}"
								alt="drinkImage">
								<input type="hidden" name="drink_id" value="${element}"></td>
							<td class= "basket-list-td">${drink.name}</td>
							<td class= "basket-list-td">${showPrice}</td>
							<td class= "basket-list-td"><input type="number" class="amount" name="drinkAmount" min="1" max="99" class="form-control" value="${amount}" onchange="recalculateBasket()" required></td>
							<td >${showSum}</td>
							<td ><button type="button" class="delete icon-image" onclick="removeDrink()"></button></td>`;
			parent.append(tr);
		}
		document.getElementById("total").innerHTML = retrievePriceForPrint(total);
	}
})();

function retrievePriceForPrint(price) {
	let locale = document.getElementById("language-change").value;
	let doublePrice = price / 100;
	return doublePrice.toLocaleString(locale, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

function recalculateBasket() {
	var parent = this.event.target.parentNode.parentNode;
	let id = parent.querySelector('[name="drink_id"]').value;
	let amount = parent.querySelector('[name="drinkAmount"]').value;
	let storage = localStorage.getItem("drinks");
	let drinks = JSON.parse(storage);
	drinks[id].amount = amount;
	let drinksForSave = JSON.stringify(drinks);

	localStorage.setItem("drinks", drinksForSave);

	window.location.replace("/CoffeeMachine/basket");
}

function removeDrink() {
	var parent = this.event.target.parentNode.parentNode;
	let id = parent.querySelector('[name="drink_id"]').value;
	let storage = localStorage.getItem("drinks");
	let drinks = JSON.parse(storage);
	delete drinks[id];
	let drinksForSave = JSON.stringify(drinks);
	if (drinksForSave == "{}") {
		localStorage.removeItem("drinks");
	} else {
		localStorage.setItem("drinks", drinksForSave);
	}
	window.location.replace("/CoffeeMachine/basket");
}

