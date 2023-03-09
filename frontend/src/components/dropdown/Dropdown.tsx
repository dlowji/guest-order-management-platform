import * as React from 'react';

interface IDropdownProps {
	children: React.ReactNode;
}

const Dropdown: React.FunctionComponent<IDropdownProps> = (props) => {
	return <div className="relative z-50 inline-block w-full">{props.children}</div>;
};

export default Dropdown;
