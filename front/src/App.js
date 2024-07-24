import { useState, useEffect } from "react";
import SideBar from "./components/SideBar";
import { fetchData } from "./services/api";
import "./index.css";
import DataTable from "./components/DataTable";
import { MainContextProvider } from "./contexts/MainContext";

function App() {
	return (
		<MainContextProvider>
			<div className="flex h-screen">
				<SideBar />
				<main className=" mx-24 my-7 flex-grow items-center justify-center">
					<DataTable />
				</main>
			</div>
		</MainContextProvider>

	);
}

export default App;
