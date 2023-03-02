import * as React from 'react';

interface ILabelProps {
	htmlFor: string;
	children: React.ReactNode;
}

const Label: React.FunctionComponent<ILabelProps> = ({ htmlFor, children }) => {
	return (
		<label
			htmlFor={htmlFor}
			className={`inline-block self-start text-lg capitalize-first font-medium cursor-pointer text-$gray7b`}
		>
			{children}
		</label>
	);
};

export default Label;
