import * as React from 'react';

interface IOverlayProps {
	isActive: boolean;
	onToggle: () => void;
}

const Overlay: React.FunctionComponent<IOverlayProps> = ({ isActive = false, onToggle }) => {
	return (
		<button
			className={`fixed inset-0 w-full h-full bg-slate-500 bg-opacity-50 z-20 ${
				isActive ? 'opacity-100 visible' : 'opacity-0 invisible w-0 h-0'
			} transition-opacity menu-overlay`}
			onClick={onToggle}
		></button>
	);
};

export default Overlay;
