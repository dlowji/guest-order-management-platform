import * as React from 'react';

interface IMainContentHeaderProps {
	title: string;
	quantity?: string;
	children?: React.ReactNode;
}

const MainContentHeader: React.FunctionComponent<IMainContentHeaderProps> = ({
	title,
	quantity,
	children,
}) => {
	return (
		<div className="main-content-header">
			<h3 className="main-content-title">{title}</h3>
			{quantity && <span className="main-content-quantity">{quantity}</span>}
			{children}
		</div>
	);
};

export default MainContentHeader;
