import React, { createContext, useContext, useState } from 'react';

interface IDropdownContext {
	show: boolean;
	handleToggleDropdown: () => void;
	setShow: (show: boolean) => void;
}

const DropdownContext = createContext<IDropdownContext | null>(null);

const DropdownProvider: React.FC<{
	children: React.ReactNode;
}> = (props) => {
	const [show, setShow] = useState(false);
	const handleToggleDropdown = () => {
		setShow(!show);
	};
	return (
		<DropdownContext.Provider value={{ show, handleToggleDropdown, setShow }}>
			{props.children}
		</DropdownContext.Provider>
	);
};
function useDropdown() {
	const context = useContext(DropdownContext);
	if (typeof context === 'undefined' || context === null)
		throw new Error('useDropdown must be used within DropdownProvider');
	return context;
}
export { useDropdown, DropdownProvider };
