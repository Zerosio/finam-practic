import React, { createContext, useState, useContext } from "react";

const MainContext = createContext();

export const MainContextProvider = ({ children }) => {
	const [filters, setFilters] = useState({
		tickerName: "",
		type: "Акции",
		sector: "Финансы",
		priceFrom: 0,
		priceUpTo: 2953292,
		capitalizationFrom: 0,
		capitalizationUpTo: 6883645978240,
		volumeFrom: 0,
		volumeUpTo: 1236600886,
		sortBy: "price",
		sortOrder: "desc",
	});

	const [isSidebarOpen, setSidebarOpen] = useState(true);

	return (
		<MainContext.Provider
			value={{
				filters,
				setFilters,
				isSidebarOpen,
				setSidebarOpen,
			}}
		>
			{children}
		</MainContext.Provider>
	);
};

export function useMainContext() {
	const context = useContext(MainContext);
	if (!context) {
		throw new Error(
			"useMainContext должен использоваться внутри MainContextProvider"
		);
	}
	return context;
}
