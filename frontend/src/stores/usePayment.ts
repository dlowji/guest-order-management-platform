import { create } from 'zustand';
interface IPayment {
	orderId: string;
	currentStep: number;
	totalSteps: number;
}

interface IUsePayment {
	payment: IPayment;
	setPayment: (payment: IPayment) => void;
	nextStep: () => void;
	prevStep: () => void;
	resetStep: () => void;
}

export const usePayment = create<IUsePayment>((set) => ({
	payment: {
		orderId: '',
		currentStep: 0,
		totalSteps: 0,
	},
	setPayment: (payment) => set({ payment }),
	nextStep: () =>
		set((state) => {
			const { currentStep } = state.payment;
			const nextStep = currentStep + 1;
			return {
				payment: {
					...state.payment,
					currentStep: nextStep,
				},
			};
		}),
	prevStep: () =>
		set((state) => {
			const { currentStep } = state.payment;
			const prevStep = currentStep - 1;
			if (prevStep < 0) return state;
			return {
				payment: {
					...state.payment,
					currentStep: prevStep,
				},
			};
		}),
	resetStep: () => {
		set((state) => {
			return {
				payment: {
					...state.payment,
					currentStep: 0,
				},
			};
		});
	},
}));
