/** @type {import('tailwindcss').Config} */
module.exports = {
	content: ["./src/**/*.{js,jsx}"],
	theme: {
		fontFamily: {
			roboto: ["Roboto", "sans-serif"],
		},
		extend: {
			colors: {
				primary: "#ABD5D6",
				border_primary: "#00616A",
			},
		},
	},
	plugins: [],
};
