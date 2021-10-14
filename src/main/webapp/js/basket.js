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
			tr.innerHTML = `<input type="hidden" name="drinkId" value="${element}">
							<td><img class="basket-list-image"
								src="${drink.image}"
								alt="drinkImage"></td>
							<td class= "basket-list-td">${drink.name}</td>
							<td class= "basket-list-td">${showPrice}</td>
							<td class= "basket-list-td"><input type="number" class="amount" name="drinkAmount" min="1" max="99" class="form-control" value="${amount}" required></td>
							<input type="hidden" name="drinkAmount" value="${amount}">
							<td >${showSum}</td>
							<td class="delete icon-image"></td>`;
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