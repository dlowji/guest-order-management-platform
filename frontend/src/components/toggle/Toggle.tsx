/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import * as React from 'react';

interface IToggleProps {
	on: boolean;
	onClick: () => void;
}

const Toggle: React.FunctionComponent<IToggleProps> = ({ on, onClick }) => {
	return (
		<label className="relative inline-flex items-center cursor-pointer">
			<input type="checkbox" value="" className="sr-only" />
			<div
				onClick={onClick}
				className={`w-11 h-6 bg-gray-200 focus:outline-none rounded-full peer after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all ${
					on ? `after:translate-x-full after:border-white bg-primaryff peer` : ''
				}`}
			></div>
		</label>
	);
};

export default Toggle;
