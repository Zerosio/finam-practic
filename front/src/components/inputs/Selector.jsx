import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import { useFormContext } from "react-hook-form";

const Selector = ({ optionsData, name }) => {
	const { register } = useFormContext();

	return (
		<div className="relative">
			<select
				{...register(name)}
				className="block px-2 py-3 appearance-none w-full bg-white border-2 border-gray-300 hover:ring-0 hover:outline-none hover:border-border_primary rounded-lg focus:outline-none items-center justify-center"
			>
				{optionsData.map((option) => (
					<option key={option.value} value={option.value}>
						{option.text}
					</option>
				))}
			</select>
			<div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
				<KeyboardArrowDownIcon />
			</div>
		</div>
	);
};

export default Selector;
