import { BrowserRouter, Routes, Route } from "react-router-dom";
import Homepage from './pages/LoginRegister';
import Dashboard from "./pages/Dashboard";
import Chamado from './pages/Chamado';

function RoutesApp(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={ <Homepage/>} />
                
                <Route path="/dashboard" element={ <Dashboard/>} />

                <Route path="/chamado/create" element={ <Chamado/>} />
            </Routes>
        </BrowserRouter>
    )
}

export default RoutesApp;