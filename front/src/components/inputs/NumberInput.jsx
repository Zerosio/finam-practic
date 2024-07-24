import { useFormContext } from "react-hook-form";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import { useState } from "react";

const NumberInput = ({ name, min, max, step }) => {
	const [isFocused, setIsFocused] = useState(false);
	const { register, setValue, watch } = useFormContext();
	const value = watch(name);

	const intRegExp = /^([1-9][0-9]*|0)?$/;

	const handleInputChange = (e) => {
		const newValue = e.target.value;

		if (!intRegExp.test(newValue)) {
			return; // Не позволяем ввести недопустимые символы
		}

		const numericValue = Number(newValue);
		if (numericValue < min || numericValue > max) {
			setValue(
				name,
				Math.max(Math.min(numericValue, max), min).toString()
			);
		} else {
			setValue(name, newValue);
		}
	};

	const incrementValue = () => {
		const numericValue = Number(value) || 0;
		if (numericValue < max) {
			setValue(name, Math.min(numericValue + step, max).toString());
		} else {
			setValue(name, min.toString());
		}
	};

	const decrementValue = () => {
		const numericValue = Number(value) || 0;
		if (numericValue > min) {
			setValue(name, Math.max(numericValue - step, min).toString());
		} else {
			setValue(name, max.toString());
		}
	};

	return (
		<div
			className={`flex flex-row rounded-lg border-2  hover:outline-none hover:border-border_primary ${
				isFocused ? "border-border_primary" : "border-gray-300"
			}`}
		>
			<div className="flex relative">
				<input
					{...register(name)}
					id={name}
					value={value}
					onChange={handleInputChange}
					onFocus={() => setIsFocused(true)}
					onBlur={() => setIsFocused(false)}
					className={`text-sm rounded-l-md pl-2.5 bg-white hover:ring-0 focus:outline-none appearance-none ${
						isFocused ? "" : "text-white"
					}`}
				/>
				<label
					htmlFor={name}
					className={`absolute text-sm left-3 top-4 ${
						isFocused ? "hidden" : ""
					}`}
				>
					{new Intl.NumberFormat("ru-RU", {
						notation: "compact",
						compactDisplay: "short",
						minimumFractionDigits: 0,
						maximumFractionDigits: 2,
					}).format(value)}
				</label>
			</div>

			<div className="flex flex-col bg-white rounded-r-md border-l-2 border-border_primary">
				<button
					type="button"
					className="border-b border-black hover:bg-green-200 active:bg-green-500 rounded-tr-lg"
					onClick={incrementValue}
					onMouseOver={() => setIsFocused(true)}
					onMouseOut={() => setIsFocused(false)}
				>
					<ArrowDropUpIcon className="text-green-700" />
				</button>
				<button
					type="button"
					className="hover:bg-green-200 active:bg-green-500 rounded-br-lg"
					onClick={decrementValue}
					onMouseOver={() => setIsFocused(true)}
					onMouseOut={() => setIsFocused(false)}
				>
					<ArrowDropDownIcon className="text-green-700" />
				</button>
			</div>
		</div>
	);
};

export default NumberInput;
