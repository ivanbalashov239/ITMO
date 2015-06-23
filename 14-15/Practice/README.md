<head>
<script type="text/javascript"
            src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
    </script>

# Отчет о работе

## Подробное описание

В ходе работы реализована небольшая библеотека на языке `haskell` для работы с просто типизированными лямбда термами.

### Типы данных

Для ввода термов используется следующая структура данных
```haskell
infixl 2 :@
infixr 3 :->

type Symb = String 

-- Терм
data Expr = 
  Var Symb 
  | Expr :@ Expr
  | Lam Symb Expr
     deriving (Eq,Show)

-- Тип
data Type = 
  TVar Symb 
  | Type :-> Type
    deriving (Eq,Show)

-- Контекст
newtype Env = Env [(Symb,Type)]
  deriving (Eq,Show)

-- Подстановка
newtype SubsTy = SubsTy [(Symb, Type)]
  deriving (Eq,Show)
```
Здесь последовательность `:->` эквивалент `.` в классическом представлении лямбда терма, а `:@` обозначает аппликацию.

Таким образом терм \\[ \lambda x.y z \\] будет представлятся в виде `Lam x :-> y :@ z`
</head>
