import FilterForm from "./FilterForm";
import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";
import { useMainContext } from "../contexts/MainContext";

const SideBar = () => {
	const { isSidebarOpen, setSidebarOpen } = useMainContext();

	const toggleSidebar = () => {
		setSidebarOpen(!isSidebarOpen);
	};

	return (
		<aside>
			<button
				className={`w-12 h-12 fixed top-4 left-4 z-50 bg-[#0e3b3f] text-white p-2 rounded-md transform transition-transform duration-300 ease-in-out ${
					isSidebarOpen ? "translate-x-[300px]" : ""
				}`}
				onClick={toggleSidebar}
			>
				{isSidebarOpen ? <CloseIcon /> : <MenuIcon />}
			</button>
			<div
				className={`overflow-y-auto fixed top-0 left-0 h-full bg-primary border-r-4 border-border_primary shadow-2xl transform transition-transform duration-300 ease-in-out z-40 ${
					isSidebarOpen ? "translate-x-0" : "-translate-x-full"
				}`}
				style={{ width: "300px" }}
			>
				<div className="p-4">
					<FilterForm />
				</div>
			</div>
		</aside>
	);
};

export default SideBar;
