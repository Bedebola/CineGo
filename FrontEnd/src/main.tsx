import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import AppRoutes from './route'
import './index.css'
import { store } from './Redux/store'
import { Provider } from 'react-redux'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
    <BrowserRouter>
    <AppRoutes/>
    </BrowserRouter>
    </Provider>
  </StrictMode>,
)
