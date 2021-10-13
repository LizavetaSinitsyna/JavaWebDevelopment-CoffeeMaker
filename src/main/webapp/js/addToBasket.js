function addToBasket() {
	let parent = this.event.target.parentNode;
	let id = parent.querySelector('[name="drink_id"]').value;
	let name = parent.querySelector('[name="name"]').value;
	let image = parent.querySelector('[name="image"]').value
	let priceInt = parent.querySelector('[name="priceInt"]').value
	let priceFractional = parent.querySelector('[name="priceFractional"]').value
	let price = priceInt * 100 + parseInt(priceFractional);

	let storage = localStorage.getItem("drinks");
	let drinks;
	if (storage) {
		drinks = JSON.parse(storage);
		let drink = drinks[id];
		if (drink) {
			drinks[id].amount++;
		} else {
			drinks[id] = { "name": name, "image": image, "amount": 1, "price": price };
		}
	} else {
		drinks = {};
		drinks[id] = { "name": name, "image": image, "amount": 1, "price": price };
	}

	localStorage.setItem("drinks", JSON.stringify(drinks));
}