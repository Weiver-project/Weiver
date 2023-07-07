const btnLike = document.querySelector(".like");
const btnView = document.querySelector(".viewed");

btnLike.addEventListener("click", (e) => {
  btnLike.style.color = "#EFEFEF";
  btnView.style.color = "#586A85";
});

btnView.addEventListener("click", (e) => {
  btnLike.style.color = "#586A85";
  btnView.style.color = "#EFEFEF";
});
