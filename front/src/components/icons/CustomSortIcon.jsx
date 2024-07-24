import FilterListIcon from "@mui/icons-material/FilterList";

const CustomSortIcon = (props) => {
	return (
		<FilterListIcon
			sx={{
				transform:
					props.className ===
					"MuiTableSortLabel-icon MuiTableSortLabel-iconDirectionDesc css-1ku2c56-MuiTableSortLabel-icon"
						? "rotate(0deg)"
						: "rotate(180deg)",
			}}
		/>
	);
};

export default CustomSortIcon;
