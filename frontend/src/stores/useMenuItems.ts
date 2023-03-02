import { IMenuItem } from '@interfaces/index';
import { create } from 'zustand';
import { devtools } from 'zustand/middleware';

interface IMenuItemsState {
	menuItems: IMenuItem[];
	setMenuItems: (menuItems: IMenuItem) => void;
}

const menuItems: IMenuItem[] = [
	{
		id: 1,
		image: '/images/food-1.jpeg',
		title: 'Pizza Margherita',
		price: 10,
		status: 'Available',
		icon: 'fas fa-circle-check',
	},
	{
		id: 2,
		image: '/images/food-2.jpeg',
		title: 'Pizza al Tonno',
		price: 10,
		status: 'Best seller',
		icon: 'fas fa-star',
	},
	{
		id: 3,
		image: '/images/food-3.jpeg',
		title: 'Pizza quattro stagioni',
		price: 10,
		status: 'Available',
		icon: 'fas fa-circle-check',
	},
	{
		id: 4,
		image: '/images/food-4.jpeg',
		title: 'Pasta alla carbonara',
		price: 10,
		status: 'Best seller',
		icon: 'fas fa-star',
	},
	{
		id: 5,
		image: '/images/food-5.jpeg',
		title: 'Pasta tonno e cipolla',
		price: 10,
		status: 'Best seller',
		icon: 'fas fa-star',
	},
	{
		id: 6,
		image: '/images/food-6.jpeg',
		title: 'Burger con patatine',
		price: 10,
		status: 'Must try',
		icon: 'fas fa-fire',
	},
	{
		id: 7,
		image: '/images/food-7.jpeg',
		title: 'Burger zucchine e pomodoro',
		price: 10,
		status: 'Available',
		icon: 'fas fa-circle-check',
	},
];

export const useMenuItems = create<IMenuItemsState>()(
	devtools(
		(set) => ({
			menuItems,
			setMenuItems: (menuItems: IMenuItem) =>
				set((state) => {
					return { menuItems: [...state.menuItems, menuItems] };
				}),
		}),
		{
			name: 'Menu Items',
		},
	),
);
