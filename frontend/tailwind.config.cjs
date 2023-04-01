/** @type {import('tailwindcss').Config} */

// eslint-disable-next-line @typescript-eslint/no-var-requires
const plugin = require('tailwindcss/plugin');

// Let's create a plugin that adds utilities!
const capitalizeFirst = plugin(function ({ addUtilities }) {
	const newUtilities = {
		'.capitalize-first:first-letter': {
			textTransform: 'uppercase',
		},
	};
	addUtilities(newUtilities, ['responsive', 'hover']);
});

module.exports = {
	content: [
		'./index.html',
		'./src/**/*.{js,ts,jsx,tsx}',
		'./node_modules/tailwind-datepicker-react/dist/**/*.js',
	],
	theme: {
		extend: {
			fontFamily: {
				primary: ['Poppins', 'sans-serif'],
			},
			colors: {
				primaryff: '#ff7200',
				secondaryff: '#febb41',
				fontcolor: '#000000',
				gray7b: '#7b88a8',
				grayf7: '#f7f5f1',
				gray2d: '#2d3958',
				gray2f: '#2f2f2f',
				gray90: '#909090',
				whitef4: '#f4ece1',
				whiteff: '#f7e9e0',
				footerbg: '#1e2833',
				borderColor: '#2b353f',
				dashboardColor: '#fafafa',
				orangefe: '#fef8f2',
				orangeff: '#fef0e1',
				dineInTable: '#ffc245',
				freeTable: '#54ca92',
				orderedTable: '#ec7905',
				canceledTable: '#ff5c5c',
				grayeb: '#ebebeb',
				error: '#EB5757',
				darkbg: '#13131A',
				darkSecondary: '#1C1C24',
				softDark: '#22222C',
				darkSoft: '#24242C',
				darkStroke: '#3A3A43',
				darkRed: '#422C32',
			},
		},
	},
	plugins: [capitalizeFirst],
};
