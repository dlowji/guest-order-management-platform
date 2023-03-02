import * as React from 'react';

interface IMenuListProps {
	children: React.ReactNode;
}

const MenuList: React.FunctionComponent<IMenuListProps> = ({ children }) => {
	return <div className="menu-list">{children}</div>;
};

export default MenuList;
