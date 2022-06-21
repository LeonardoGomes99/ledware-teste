import { createContext, useState } from "react";

export const ChamadoContext = createContext({});


function ChamadoProvider({children}){

    const [chamado, setChamado] = useState({});

    return(
        <ChamadoContext.Provider value={{chamado, setChamado}}>
            {children}
        </ChamadoContext.Provider>
    )
}

export default ChamadoProvider;