import { useDropdown } from '@context/useDropdown';
import * as React from 'react';

interface IDropdownListProps {
	classNameBody?: string;
	children?: React.ReactNode;
}

const DropdownList: React.FunctionComponent<IDropdownListProps> = ({ children, classNameBody }) => {
	const { show } = useDropdown();
	return (
		<>
			{show && (
				<div
					className={`absolute top-full right-0 bg-white shadow-sm z-10 border border-solid border-gray-300 rounded-[10px] ${classNameBody} w-[200px] overflow-hidden`}
				>
					{children}
				</div>
			)}
		</>
	);
};

export default DropdownList;
