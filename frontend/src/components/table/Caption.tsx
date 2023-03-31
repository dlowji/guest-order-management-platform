import * as React from 'react';

interface ICaptionProps {
	children?: React.ReactNode;
	className?: string;
}

const Caption: React.FunctionComponent<ICaptionProps> = ({ className, children }) => {
	return (
		<caption
			className={`${className || ''} p-5 text-lg font-semibold text-left text-gray-900 bg-white`}
		>
			{children}
		</caption>
	);
};

export default Caption;
