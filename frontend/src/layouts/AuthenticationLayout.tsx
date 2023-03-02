import * as React from 'react';
import { Outlet } from 'react-router-dom';

interface IAuthenticationLayoutProps {
	heading: string;
	subheading: string;
}

const AuthenticationLayout: React.FunctionComponent<IAuthenticationLayoutProps> = ({
	heading,
	subheading,
}) => {
	return (
		<section className="h-screen">
			<div className="container px-6 py-12 h-full">
				<div className="flex justify-center items-center flex-wrap h-full text-gray-800">
					<div className="md:w-8/12 lg:w-6/12 mb-12 md:mb-0">
						<img src="/images/bg.png" className="w-full" alt="Background" />
					</div>
					<div className="md:w-8/12 lg:w-5/12 lg:ml-20">
						<h1 className="text-4xl font-bold mb-4 text-center">{heading}</h1>
						<p className="text-gray-500 text-center mb-8">{subheading}</p>
						<Outlet></Outlet>
					</div>
				</div>
			</div>
		</section>
	);
};

export default AuthenticationLayout;
