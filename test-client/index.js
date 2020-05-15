const root_url = 'https://acb0e094.ngrok.io/TestingAssignmentW9_war_exploded';

const options = (body) => {
	return {
		headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json',
		},
		method: 'POST',
		body: JSON.stringify(body),
	};
};

// bank requests
const getBanks = async () => {
	const response = await fetch(`${root_url}/banks/all`);
	const data = await response.json();
	return data;
};
const getBank = async (cvr) => {
	const response = await fetch(`${root_url}/banks/bank/${cvr}`);
	if (response.status != 200) return { error: response.status };
	const data = await response.json();
	return data;
};
const addBank = async (name, cvr) => {
	const response = await fetch(`${root_url}/banks/add/`, options({ name, cvr }));
	const data = await response.status;
	return data;
};

// customer requests
const getCustomers = async () => {
	const response = await fetch(`${root_url}/customers/all`);
	const data = await response.json();
	return data;
};
const getCustomer = async (cpr) => {
	const response = await fetch(`${root_url}/customers/customer/${cpr}`);
	if (response.status != 200) return { error: response.status };
	const data = await response.json();
	return data;
};
const addCustomer = async (name, cpr, cvr) => {
	const response = await fetch(`${root_url}/customers/add/`, options({ name, cpr, cvr }));
	const data = await response.status;
	return data;
};

// account requests
const getAccounts = async () => {
	const response = await fetch(`${root_url}/accounts/all`);
	const data = await response.json();
	return data;
};
const getAccount = async (id) => {
	const response = await fetch(`${root_url}/accounts/account/${id}`);
	if (response.status != 200) return { error: response.status };
	const data = await response.json();
	return data;
};
const getAllAccountsFromCustomer = async (cpr) => {
	const response = await fetch(`${root_url}/accounts/${cpr}`);
	const data = await response.json();
	return data;
};
const addAccount = async (cpr, number) => {
	const response = await fetch(`${root_url}/accounts/add/`, options({ cpr, number }));
	const data = await response.status;
	return data;
};

// movement requests
const getDeposits = async (acc) => {
	const response = await fetch(`${root_url}/movements/deposits/${acc}`);
	const data = await response.json();
	return data;
};
const getWithdrawals = async (acc) => {
	const response = await fetch(`${root_url}/movements/withdrawals/${acc}`);
	const data = await response.json();
	return data;
};
/**
 * @param {long} amount The date
 * @param {String} source_number
 * @param {String} target_number
 **/
//                         Long    String         String
const addMovement = async (amount, source_number, target_number) => {
	const response = await fetch(`${root_url}/movements/add`, options({ amount, source_number, target_number }));
	return await response.status;
};

document.addEventListener('DOMContentLoaded', async () => {
	// response = await addBank('second bank', 'bi222');
	// console.log(response);
});
