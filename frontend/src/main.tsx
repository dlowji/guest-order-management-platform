import '@styles/index.scss';
import {
	createBrowserRouter,
	createRoutesFromElements,
	Route,
	RouterProvider,
} from 'react-router-dom';
import { lazy, Suspense } from 'react';
import ReactDOM from 'react-dom/client';
import Fallback from '@components/common/Fallback';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import MenuRightContent from '@modules/common/MenuRightContent';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import ProtectedRoute from '@modules/common/ProtectedRoute';
import Role from '@constants/ERole';
import KitchenOrder from '@modules/kitchen/KitchenOrder';
import { OrderDetailProvider } from '@context/useOrderDetail';
import RedirectPage from '@pages/RedirectPage';
import ErrorPage from '@pages/ErrorPage';

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

const KitchenPage = lazy(() =>
	import('@pages/KitchenPage').then((module) => ({ default: module.default })),
);

const CheckoutPage = lazy(() =>
	import('@pages/CheckoutPage').then((module) => ({ default: module.default })),
);

const HistoryPage = lazy(() =>
	import('@pages/HistoryPage').then((module) => ({ default: module.default })),
);

const router = createBrowserRouter(
	createRoutesFromElements(
		<Route errorElement={<ErrorPage />}>
			<Route element={<MainLayout />}>
				<Route element={<ProtectedRoute allowedRoles={[Role.ADMIN]} />}>
					<Route path="/home" element={<DashboardPage />}></Route>
					<Route path="/history" element={<HistoryPage />}></Route>
				</Route>
				<Route element={<ProtectedRoute allowedRoles={[Role.ADMIN, Role.EMPLOYEE, Role.CHEF]} />}>
					<Route path="/" element={<RedirectPage />}></Route>
					<Route path="/table" element={<TablePage />} />
					<Route path="/menu" element={<MenuPage />}>
						<Route path="/menu/order/:id" element={<MenuRightContent></MenuRightContent>} />
					</Route>
					<Route path="/order" element={<OrderPage />}></Route>
				</Route>
				<Route element={<ProtectedRoute allowedRoles={[Role.CHEF]} />}>
					<Route path="/kitchen" element={<KitchenPage />}></Route>
					<Route
						path="/kitchen/:orderId"
						element={
							<OrderDetailProvider>
								<KitchenOrder />
							</OrderDetailProvider>
						}
					></Route>
				</Route>
			</Route>
			<Route element={<ProtectedRoute allowedRoles={[Role.ADMIN, Role.EMPLOYEE]} />}>
				<Route path="/checkout/:orderId" element={<CheckoutPage />}></Route>
				<Route path="/checkout/:orderId/:step" element={<CheckoutPage />}></Route>
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
		</Route>,
	),
);

const queryClient = new QueryClient({
	defaultOptions: {
		queries: {
			refetchOnWindowFocus: false,
			staleTime: 1000 * 30 * 1,
			cacheTime: 1000 * 60 * 1,
		},
	},
});

document.addEventListener('DOMContentLoaded', function () {
	const container = document.getElementById('root') as HTMLElement;
	if (!container) throw new Error('No root element found!');
	const root = ReactDOM.createRoot(container);
	root.render(
		<>
			<Suspense fallback={<Fallback />}>
				<QueryClientProvider client={queryClient}>
					<RouterProvider router={router}></RouterProvider>
					<ReactQueryDevtools initialIsOpen={false} />
				</QueryClientProvider>
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
