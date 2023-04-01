import Role from '@constants/ERole';
import { useAuth } from '@stores/useAuth';
import * as React from 'react';
import { useNavigate } from 'react-router-dom';

interface IRedirectPageProps {}

const RedirectPage: React.FunctionComponent<IRedirectPageProps> = (props) => {
	const auth = useAuth();
	const navigate = useNavigate();
	React.useEffect(() => {
		if (auth.user?.roleName === Role.ADMIN) {
			navigate('/home');
		}
		if (auth.user?.roleName === Role.EMPLOYEE) {
			navigate('/table');
		}
		if (auth.user?.roleName === Role.CHEF) {
			navigate('/menu');
		}
	}, [auth.user?.roleName]);
	return <></>;
};

export default RedirectPage;
