import * as React from 'react';

interface IFormGroupProps {
	children: React.ReactNode;
	className?: string;
}

const FormGroup: React.FunctionComponent<IFormGroupProps> = ({ children, className }) => {
	return (
		<div className={`flex mb-4 lg:mb-6 gap-y-2 lg:gap-y-3 flex-col ${className}`}>{children}</div>
	);
};

export default FormGroup;
