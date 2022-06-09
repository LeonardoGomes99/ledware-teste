import { Link } from 'react-router-dom'


function Home() {
    return (
      <div>
          <h1>Bem Vindo a Home</h1>
          <Link to="/dashboard">Para Dashboard</Link>
      </div>      
    );
  }
  
  export default Home;
  