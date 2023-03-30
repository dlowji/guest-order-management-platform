import * as React from 'react';

interface ITBodyProps {
	children: React.ReactNode;
	className?: string;
}

const TBody: React.FunctionComponent<ITBodyProps> = ({ children, className }) => {
	return <tbody className={className}>{children}</tbody>;
};

export default TBody;
