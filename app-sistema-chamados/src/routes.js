import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from './pages/Home';
import Dashboard from './pages/Dashboard';

import ChamadoCreate from './pages/ChamadoCreate';
import ChamadoEdit from './pages/ChamadoEdit';

import NotFound from  './pages/NotFound';
import Header from './components/Header';


function RoutesApp(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={ <Home/>} />

                <Route path="/dashboard" element={ <Dashboard/>} />

                <Route path="/chamado" element={ <ChamadoCreate/>} />

                <Route path="/chamado/:id" element={ <ChamadoEdit/>} />

                <Route path="/*" element={ <NotFound/>} />
            </Routes>
        </BrowserRouter>
    )
}

export default RoutesApp;