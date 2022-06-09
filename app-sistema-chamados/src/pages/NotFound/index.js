import { Link } from 'react-router-dom'


function Page404(){
    return(
        <div>
            <h1>404 NÃO ENCONTRADO</h1>
            <Link to="/dashboard">Para Dashboard</Link>
        </div>
    )
}

export default Page404