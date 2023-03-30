import { useState } from 'react';

interface ProgressBarProps {
	currentStep: number;
	totalSteps: number;
}

const useSteps = (totalSteps: number, step?: number) => {
	const [currentStep, setCurrentStep] = useState<number>(step ? step : 0);

	const nextStep = () => {
		setCurrentStep((prev) => prev + 1);
	};

	const prevStep = () => {
		setCurrentStep((prev) => prev - 1);
	};

	const resetSteps = () => {
		setCurrentStep(0);
	};

	return {
		currentStep,
		totalSteps,
		nextStep,
		prevStep,
		resetSteps,
		setCurrentStep,
	};
};

export default useSteps;
