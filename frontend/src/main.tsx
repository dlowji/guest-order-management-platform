import {
	createBrowserRouter,
	createRoutesFromElements,
	Route,
	RouterProvider,
} from 'react-router-dom';
import { lazy, Suspense } from 'react';
import ReactDOM from 'react-dom/client';
import '@styles/index.scss';
import Fallback from '@components/common/Fallback';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import MenuRightContent from '@modules/common/MenuRightContent';

const MainLayout = lazy(() =>
	import('@layouts/MainLayout').then((module) => ({ default: module.default })),
);

const DashboardPage = lazy(() =>
	import('@pages/DashboardPage').then((module) => ({ default: module.default })),
);

const TablePage = lazy(() =>
	import('@pages/TablePage').then((module) => ({ default: module.default })),
);

const MenuPage = lazy(() =>
	import('@pages/MenuPage').then((module) => ({ default: module.default })),
);

const AuthenticationLayout = lazy(() =>
	import('@layouts/AuthenticationLayout').then((module) => ({ default: module.default })),
);

const LoginPage = lazy(() =>
	import('@pages/LoginPage').then((module) => ({ default: module.default })),
);

const OrderPage = lazy(() =>
	import('@pages/OrderPage').then((module) => ({ default: module.default })),
);

const router = createBrowserRouter(
	createRoutesFromElements(
		<>
			<Route element={<MainLayout />}>
				<Route path="/" element={<DashboardPage />} />
				<Route path="/table" element={<TablePage />} />
				<Route path="/menu" element={<MenuPage />}>
					<Route path="/menu/order/:id" element={<MenuRightContent></MenuRightContent>} />
				</Route>
				<Route path="/order" element={<OrderPage />}>
					<Route path="/order/:id" element={<MenuRightContent></MenuRightContent>} />
				</Route>
			</Route>
			<Route
				element={
					<AuthenticationLayout
						heading="Welcome back!"
						subheading="Sign in to your account to continue"
					/>
				}
			>
				<Route path="/login" element={<LoginPage></LoginPage>} />
			</Route>
		</>,
	),
);

document.addEventListener('DOMContentLoaded', function () {
	const container = document.getElementById('root') as HTMLElement;
	if (!container) throw new Error('No root element found!');
	const root = ReactDOM.createRoot(container);
	root.render(
		<>
			<Suspense fallback={<Fallback />}>
				<RouterProvider router={router}></RouterProvider>
			</Suspense>
			<ToastContainer
				bodyClassName="font-primary text-sm"
				pauseOnFocusLoss={false}
				pauseOnHover={false}
				autoClose={2000}
			></ToastContainer>
		</>,
	);
});
